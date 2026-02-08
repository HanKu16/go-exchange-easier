CREATE TYPE role AS ENUM (
  'RoleUser',
  'RoleAdmin'
);

CREATE TYPE reaction_type AS ENUM (
  'Like',
  'Dislike'
);

CREATE TABLE countries (
  country_id SMALLSERIAL PRIMARY KEY,
  english_name VARCHAR(60) UNIQUE NOT NULL
);

CREATE TABLE cities (
  city_id SERIAL PRIMARY KEY,
  english_name VARCHAR(60) NOT NULL,
  country_id SMALLINT NOT NULL
);

CREATE TABLE universities (
  university_id SMALLSERIAL PRIMARY KEY,
  original_name VARCHAR(500) NOT NULL,
  english_name VARCHAR(255),
  link_to_website VARCHAR(2048),
  deleted_at TIMESTAMPTZ,
  city_id INTEGER NOT NULL
);

CREATE TABLE fields_of_study (
  field_of_study_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_statuses (
  user_status_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  nick VARCHAR(20) NOT NULL,
  avatar_key VARCHAR(255),
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  user_status_id SMALLINT,
  home_university_id SMALLINT,
  country_of_origin_id SMALLINT
);

CREATE TABLE user_descriptions (
  user_id INTEGER PRIMARY KEY,
  text_content VARCHAR(500) NOT NULL,
  updated_at TIMESTAMPTZ
);

CREATE TABLE user_notifications (
  user_id INTEGER PRIMARY KEY,
  mail VARCHAR(254) UNIQUE,
  is_mail_notification_enabled BOOLEAN NOT NULL
);

CREATE TABLE user_credentials (
  user_id INTEGER PRIMARY KEY,
  username VARCHAR(20) UNIQUE NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE user_roles (
  user_id INTEGER,
  role ROLE,
  PRIMARY KEY (user_id, role)
);

CREATE TABLE university_reviews (
  university_review_id SERIAL PRIMARY KEY,
  text_content VARCHAR(1000) NOT NULL,
  star_rating SMALLINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  author_id INTEGER NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE university_review_reactions (
  university_review_reaction_id BIGSERIAL PRIMARY KEY,
  reaction_type REACTION_TYPE NOT NULL,
  university_review_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL
);

CREATE TABLE university_reviews_reaction_counts (
  university_review_id INTEGER,
  reaction_type REACTION_TYPE,
  count SMALLINT NOT NULL,
  PRIMARY KEY (university_review_id, reaction_type)
);

CREATE TABLE user_follows (
  follower_id INTEGER NOT NULL,
  followee_id INTEGER NOT NULL,
  PRIMARY KEY (follower_id, followee_id)
);

CREATE TABLE university_follows (
  follower_id INTEGER NOT NULL,
  university_id SMALLINT NOT NULL,
  PRIMARY KEY (follower_id, university_id)
);

CREATE TABLE exchanges (
  exchange_id SERIAL PRIMARY KEY,
  started_at DATE NOT NULL,
  end_at DATE NOT NULL,
  user_id INTEGER NOT NULL,
  field_of_study_id SMALLINT NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE refresh_tokens (
  refresh_token_id UUID PRIMARY KEY,
  hashed_token TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  expires_at TIMESTAMPTZ NOT NULL,
  is_revoked BOOLEAN NOT NULL,
  device_id UUID NOT NULL,
  device_name VARCHAR(255),
  ip_address VARCHAR(45),
  user_id INTEGER NOT NULL
);

ALTER TABLE cities ADD FOREIGN KEY (country_id) REFERENCES countries (country_id);

ALTER TABLE universities ADD FOREIGN KEY (city_id) REFERENCES cities (city_id);

ALTER TABLE user_credentials ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_descriptions ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_notifications ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE users ADD FOREIGN KEY (user_status_id) REFERENCES user_statuses (user_status_id);

ALTER TABLE users ADD FOREIGN KEY (home_university_id) REFERENCES universities (university_id);

ALTER TABLE users ADD FOREIGN KEY (country_of_origin_id) REFERENCES countries (country_id);

ALTER TABLE user_roles ADD FOREIGN KEY (user_id) REFERENCES user_credentials (user_id);

ALTER TABLE university_reviews ADD FOREIGN KEY (author_id) REFERENCES users (user_id);

ALTER TABLE university_reviews ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE university_review_reactions ADD FOREIGN KEY (university_review_id) REFERENCES university_reviews (university_review_id);

ALTER TABLE university_review_reactions ADD FOREIGN KEY (author_id) REFERENCES users (user_id);

ALTER TABLE university_reviews_reaction_counts ADD FOREIGN KEY (university_review_id) REFERENCES university_reviews (university_review_id);

ALTER TABLE user_follows ADD FOREIGN KEY (follower_id) REFERENCES users (user_id);

ALTER TABLE user_follows ADD FOREIGN KEY (followee_id) REFERENCES users (user_id);

ALTER TABLE university_follows ADD FOREIGN KEY (follower_id) REFERENCES users (user_id);

ALTER TABLE university_follows ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE exchanges ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE exchanges ADD FOREIGN KEY (field_of_study_id) REFERENCES fields_of_study (field_of_study_id);

ALTER TABLE exchanges ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE refresh_tokens ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_cities_country_id ON cities (country_id);

CREATE INDEX idx_universities_city_id ON universities (city_id);

CREATE INDEX idx_universities_original_name_trgm ON universities 
USING GIN (lower(original_name) gin_trgm_ops);

CREATE INDEX idx_universities_english_name_trgm ON universities
USING GIN (lower(english_name) gin_trgm_ops);

CREATE INDEX idx_users_nick ON users (nick);

CREATE INDEX idx_user_notifications_mail ON user_notifications (mail);

CREATE INDEX idx_user_credentials ON user_credentials (username);

CREATE INDEX idx_university_reviews_author_id ON university_reviews (author_id);

CREATE INDEX idx_university_reviews_university_id ON university_reviews (university_id);

CREATE INDEX idx_university_reviews_reaction_counts_university_review_id ON university_reviews_reaction_counts (university_review_id);

CREATE INDEX idx_exchanges_started_at ON exchanges (started_at);

CREATE INDEX idx_exchanges_end_at ON exchanges (end_at);

CREATE INDEX idx_exchanges_field_of_study_id ON exchanges (field_of_study_id);

CREATE INDEX idx_exchanges_university_id ON exchanges (university_id);

