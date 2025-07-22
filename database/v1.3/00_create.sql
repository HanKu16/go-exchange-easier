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
  city_id INTEGER NOT NULL
);

CREATE TABLE university_majors (
  university_major_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_statuses (
  user_status_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  nick VARCHAR(20) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  country_of_origin_id SMALLINT,
  home_university_id SMALLINT,
  user_status_id SMALLINT
);

CREATE TABLE roles (
  role_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(20) UNIQUE NOT NULL,
  description VARCHAR(200)
);

CREATE TABLE user_roles (
  user_id INTEGER,
  role_id SMALLINT,
  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE user_credentials (
  user_id INTEGER PRIMARY KEY,
  username VARCHAR(20) UNIQUE NOT NULL,
  password TEXT NOT NULL,
  is_enabled BOOLEAN NOT NULL
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

CREATE TABLE university_reviews (
  university_review_id SERIAL PRIMARY KEY,
  text_content VARCHAR(1000) NOT NULL,
  star_rating SMALLINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  author_id INTEGER NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE reaction_types (
  reaction_type_id SMALLSERIAL PRIMARY KEY,
  name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE reactions_on_university_reviews (
  reaction_on_university_review_id BIGSERIAL PRIMARY KEY,
  reaction_type_id SMALLINT NOT NULL,
  university_review_id INTEGER NOT NULL,
  author_id INTEGER NOT NULL
);

CREATE TABLE university_reviews_reaction_counts (
  university_review_id INTEGER,
  reaction_type_id SMALLINT,
  count SMALLINT NOT NULL,
  PRIMARY KEY (university_review_id, reaction_type_id)
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
  university_id SMALLINT NOT NULL,
  university_major_id SMALLINT NOT NULL
);

ALTER TABLE cities ADD FOREIGN KEY (country_id) REFERENCES countries (country_id);

ALTER TABLE universities ADD FOREIGN KEY (city_id) REFERENCES cities (city_id);

ALTER TABLE users ADD FOREIGN KEY (country_of_origin_id) REFERENCES countries (country_id);

ALTER TABLE users ADD FOREIGN KEY (home_university_id) REFERENCES universities (university_id);

ALTER TABLE users ADD FOREIGN KEY (user_status_id) REFERENCES user_statuses (user_status_id);

ALTER TABLE user_roles ADD FOREIGN KEY (user_id) REFERENCES user_credentials (user_id);

ALTER TABLE user_roles ADD FOREIGN KEY (role_id) REFERENCES roles (role_id);

ALTER TABLE user_credentials ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_descriptions ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_notifications ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE university_reviews ADD FOREIGN KEY (author_id) REFERENCES users (user_id);

ALTER TABLE university_reviews ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE reactions_on_university_reviews ADD FOREIGN KEY (reaction_type_id) REFERENCES reaction_types (reaction_type_id);

ALTER TABLE reactions_on_university_reviews ADD FOREIGN KEY (university_review_id) REFERENCES university_reviews (university_review_id);

ALTER TABLE reactions_on_university_reviews ADD FOREIGN KEY (author_id) REFERENCES users (user_id);

ALTER TABLE university_reviews_reaction_counts ADD FOREIGN KEY (university_review_id) REFERENCES university_reviews (university_review_id);

ALTER TABLE university_reviews_reaction_counts ADD FOREIGN KEY (reaction_type_id) REFERENCES reaction_types (reaction_type_id);

ALTER TABLE user_follows ADD FOREIGN KEY (follower_id) REFERENCES users (user_id);

ALTER TABLE user_follows ADD FOREIGN KEY (followee_id) REFERENCES users (user_id);

ALTER TABLE university_follows ADD FOREIGN KEY (follower_id) REFERENCES users (user_id);

ALTER TABLE university_follows ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE exchanges ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE exchanges ADD FOREIGN KEY (university_id) REFERENCES universities (university_id);

ALTER TABLE exchanges ADD FOREIGN KEY (university_major_id) REFERENCES university_majors (university_major_id);
