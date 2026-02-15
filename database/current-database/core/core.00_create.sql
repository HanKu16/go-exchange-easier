CREATE SCHEMA IF NOT EXISTS core ;

CREATE TYPE core.role AS ENUM (
  'RoleUser',
  'RoleAdmin'
);

CREATE TYPE core.reaction_type AS ENUM (
  'Like',
  'Dislike'
);

CREATE TABLE core.countries (
  country_id SMALLSERIAL PRIMARY KEY,
  english_name VARCHAR(60) UNIQUE NOT NULL,
  flag_key TEXT
);

CREATE TABLE core.cities (
  city_id SERIAL PRIMARY KEY,
  english_name VARCHAR(60) NOT NULL,
  country_id SMALLINT NOT NULL
);

CREATE TABLE core.universities (
  university_id SMALLSERIAL PRIMARY KEY,
  original_name VARCHAR(500) NOT NULL,
  english_name VARCHAR(255),
  link_to_website VARCHAR(2048),
  deleted_at TIMESTAMPTZ,
  city_id INTEGER NOT NULL
);

CREATE TABLE core.fields_of_study (
  field_of_study_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE core.user_statuses (
  user_status_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE core.user_credentials (
  user_id INTEGER PRIMARY KEY,
  username VARCHAR(20) UNIQUE NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE core.user_descriptions (
  user_id INTEGER PRIMARY KEY,
  text_content VARCHAR(500) NOT NULL,
  updated_at TIMESTAMPTZ
);

CREATE TABLE core.user_notifications (
  user_id INTEGER PRIMARY KEY,
  mail VARCHAR(254) UNIQUE,
  is_mail_notification_enabled BOOLEAN NOT NULL
);

CREATE TABLE core.users (
  user_id SERIAL PRIMARY KEY,
  nick VARCHAR(20) NOT NULL,
  avatar_key VARCHAR(255),
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  user_status_id SMALLINT,
  home_university_id SMALLINT,
  country_of_origin_id SMALLINT
);

CREATE TABLE core.user_roles (
  user_id INTEGER,
  role core.ROLE,
  PRIMARY KEY (user_id, role)
);

CREATE TABLE core.university_reviews (
  university_review_id SERIAL PRIMARY KEY,
  text_content VARCHAR(1000) NOT NULL,
  star_rating SMALLINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  author_id INTEGER NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE core.university_review_reactions (
  university_review_reaction_id BIGSERIAL PRIMARY KEY,
  reaction_type core.REACTION_TYPE NOT NULL,
  university_review_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL
);

CREATE TABLE core.university_reviews_reaction_counts (
  university_review_id INTEGER,
  reaction_type core.REACTION_TYPE,
  count SMALLINT NOT NULL,
  PRIMARY KEY (university_review_id, reaction_type)
);

CREATE TABLE core.user_follows (
  follower_id INTEGER NOT NULL,
  followee_id INTEGER NOT NULL,
  PRIMARY KEY (follower_id, followee_id)
);

CREATE TABLE core.university_follows (
  follower_id INTEGER NOT NULL,
  university_id SMALLINT NOT NULL,
  PRIMARY KEY (follower_id, university_id)
);

CREATE TABLE core.exchanges (
  exchange_id SERIAL PRIMARY KEY,
  started_at DATE NOT NULL,
  end_at DATE NOT NULL,
  user_id INTEGER NOT NULL,
  field_of_study_id SMALLINT NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE core.refresh_tokens (
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

ALTER TABLE core.cities ADD FOREIGN KEY (country_id) REFERENCES core.countries (country_id);

ALTER TABLE core.universities ADD FOREIGN KEY (city_id) REFERENCES core.cities (city_id);

ALTER TABLE core.user_credentials ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.user_descriptions ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.user_notifications ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.users ADD FOREIGN KEY (user_status_id) REFERENCES core.user_statuses (user_status_id);

ALTER TABLE core.users ADD FOREIGN KEY (home_university_id) REFERENCES core.universities (university_id);

ALTER TABLE core.users ADD FOREIGN KEY (country_of_origin_id) REFERENCES core.countries (country_id);

ALTER TABLE core.user_roles ADD FOREIGN KEY (user_id) REFERENCES core.user_credentials (user_id);

ALTER TABLE core.university_reviews ADD FOREIGN KEY (author_id) REFERENCES core.users (user_id);

ALTER TABLE core.university_reviews ADD FOREIGN KEY (university_id) REFERENCES core.universities (university_id);

ALTER TABLE core.university_review_reactions ADD FOREIGN KEY (university_review_id) REFERENCES core.university_reviews (university_review_id);

ALTER TABLE core.university_review_reactions ADD FOREIGN KEY (author_id) REFERENCES core.users (user_id);

ALTER TABLE core.university_reviews_reaction_counts ADD FOREIGN KEY (university_review_id) REFERENCES core.university_reviews (university_review_id);

ALTER TABLE core.user_follows ADD FOREIGN KEY (follower_id) REFERENCES core.users (user_id);

ALTER TABLE core.user_follows ADD FOREIGN KEY (followee_id) REFERENCES core.users (user_id);

ALTER TABLE core.university_follows ADD FOREIGN KEY (follower_id) REFERENCES core.users (user_id);

ALTER TABLE core.university_follows ADD FOREIGN KEY (university_id) REFERENCES core.universities (university_id);

ALTER TABLE core.exchanges ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.exchanges ADD FOREIGN KEY (field_of_study_id) REFERENCES core.fields_of_study (field_of_study_id);

ALTER TABLE core.exchanges ADD FOREIGN KEY (university_id) REFERENCES core.universities (university_id);

ALTER TABLE core.refresh_tokens ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX cities_country_id_idx ON core.cities (country_id);

CREATE INDEX universities_city_id_idx ON core.universities (city_id);

CREATE INDEX universities_orig_name_trgm_idx ON core.universities 
USING GIN (lower(original_name) gin_trgm_ops);

CREATE INDEX universities_eng_name_trgm_idx ON core.universities
USING GIN (lower(english_name) gin_trgm_ops);

CREATE INDEX users_nick_idx ON core.users (nick);

CREATE INDEX user_notifs_mail_idx ON core.user_notifications (mail);

CREATE INDEX user_creds_username_idx ON core.user_credentials (username);

CREATE INDEX uni_reviews_author_id_idx ON core.university_reviews (author_id);

CREATE INDEX uni_reviews_uni_id_idx ON core.university_reviews (university_id);

CREATE INDEX review_reactions_review_id_idx ON core.university_reviews_reaction_counts (university_review_id);

CREATE INDEX exchanges_started_at_idx ON core.exchanges (started_at);

CREATE INDEX exchanges_end_at_idx ON core.exchanges (end_at);

CREATE INDEX exchanges_fos_id_idx ON core.exchanges (field_of_study_id);

CREATE INDEX exchanges_uni_id_idx ON core.exchanges (university_id);
