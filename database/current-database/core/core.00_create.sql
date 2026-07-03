CREATE SCHEMA IF NOT EXISTS core;

CREATE TABLE core.countries (
  country_id SMALLSERIAL PRIMARY KEY,
  english_name TEXT UNIQUE NOT NULL,
  flag_key TEXT
);

CREATE TABLE core.cities (
  city_id SERIAL PRIMARY KEY,
  english_name TEXT NOT NULL,
  country_id SMALLINT NOT NULL
);

CREATE TABLE core.universities (
  university_id SMALLSERIAL PRIMARY KEY,
  original_name TEXT NOT NULL,
  english_name TEXT,
  link_to_website TEXT,
  deleted_at TIMESTAMPTZ,
  city_id INTEGER NOT NULL
);

CREATE TABLE core.fields_of_study (
  field_of_study_id SMALLSERIAL PRIMARY KEY,
  name TEXT UNIQUE NOT NULL
);

CREATE TABLE core.user_statuses (
  user_status_id SMALLSERIAL PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE core.principals (
  principal_id UUID PRIMARY KEY,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE core.user_descriptions (
  user_id UUID PRIMARY KEY,
  text_content TEXT NOT NULL,
  updated_at TIMESTAMPTZ
);

CREATE TABLE core.notification_settings (
  user_id UUID PRIMARY KEY,
  mail TEXT UNIQUE,
  is_mail_notification_enabled BOOLEAN NOT NULL
);

CREATE TABLE core.users (
  user_id UUID PRIMARY KEY,
  nick TEXT NOT NULL,
  avatar_key TEXT,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  is_blocked BOOLEAN NOT NULL,
  user_status_id SMALLINT,
  home_university_id SMALLINT,
  country_of_origin_id SMALLINT
);

CREATE TABLE core.principal_roles (
  principal_id UUID,
  role TEXT,
  PRIMARY KEY (principal_id, role)
);

CREATE TABLE core.university_reviews (
  university_review_id SERIAL PRIMARY KEY,
  text_content TEXT NOT NULL,
  star_rating SMALLINT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  author_id UUID NOT NULL,
  university_id SMALLINT NOT NULL
);

CREATE TABLE core.university_review_reactions (
  university_review_reaction_id BIGSERIAL PRIMARY KEY,
  reaction_type TEXT NOT NULL,
  university_review_id INTEGER NOT NULL,
  author_id UUID NOT NULL
);

CREATE TABLE core.university_reviews_reaction_counts (
  university_review_id INTEGER,
  reaction_type TEXT,
  count SMALLINT NOT NULL,
  PRIMARY KEY (university_review_id, reaction_type)
);

CREATE TABLE core.user_follows (
  follower_id UUID NOT NULL,
  followee_id UUID NOT NULL,
  PRIMARY KEY (follower_id, followee_id)
);

CREATE TABLE core.university_follows (
  follower_id UUID NOT NULL,
  university_id SMALLINT NOT NULL,
  PRIMARY KEY (follower_id, university_id)
);

CREATE TABLE core.exchanges (
  exchange_id SERIAL PRIMARY KEY,
  started_at DATE NOT NULL,
  end_at DATE NOT NULL,
  user_id UUID NOT NULL,
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
  device_name TEXT,
  ip_address TEXT,
  user_id UUID NOT NULL
);

CREATE TABLE core.reports (
  report_id UUID PRIMARY KEY,
  created_at TIMESTAMPTZ NOT NULL,
  description TEXT,
  status TEXT NOT NULL,
  reason TEXT NOT NULL,
  type TEXT NOT NULL,
  reporter_id UUID NOT NULL
);

CREATE TABLE core.user_reports (
  report_id UUID PRIMARY KEY,
  context JSONB NOT NULL,
  reported_user_id UUID NOT NULL
);

CREATE TABLE core.university_review_reports (
  report_id UUID PRIMARY KEY,
  context JSONB NOT NULL,
  reported_review_id INTEGER NOT NULL
);

CREATE TABLE core.chat_reports (
  report_id UUID PRIMARY KEY,
  context JSONB NOT NULL,
  reported_user_id UUID NOT NULL,
  room_id UUID NOT NULL
);

ALTER TABLE core.cities ADD FOREIGN KEY (country_id) REFERENCES core.countries (country_id);

ALTER TABLE core.universities ADD FOREIGN KEY (city_id) REFERENCES core.cities (city_id);

ALTER TABLE core.user_descriptions ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.notification_settings ADD FOREIGN KEY (user_id) REFERENCES core.users (user_id);

ALTER TABLE core.principals ADD FOREIGN KEY (principal_id) REFERENCES core.users (user_id);

ALTER TABLE core.users ADD FOREIGN KEY (user_status_id) REFERENCES core.user_statuses (user_status_id);

ALTER TABLE core.users ADD FOREIGN KEY (home_university_id) REFERENCES core.universities (university_id);

ALTER TABLE core.users ADD FOREIGN KEY (country_of_origin_id) REFERENCES core.countries (country_id);

ALTER TABLE core.principal_roles ADD FOREIGN KEY (principal_id) REFERENCES core.principals (principal_id);

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

ALTER TABLE core.reports ADD FOREIGN KEY (reporter_id) REFERENCES core.users (user_id);

ALTER TABLE core.user_reports ADD FOREIGN KEY (report_id) REFERENCES core.reports (report_id);

ALTER TABLE core.user_reports ADD FOREIGN KEY (reported_user_id) REFERENCES core.users (user_id);

ALTER TABLE core.university_review_reports ADD FOREIGN KEY (report_id) REFERENCES core.reports (report_id);

ALTER TABLE core.university_review_reports ADD FOREIGN KEY (reported_review_id) REFERENCES core.university_reviews (university_review_id);

ALTER TABLE core.chat_reports ADD FOREIGN KEY (report_id) REFERENCES core.reports (report_id);

ALTER TABLE core.chat_reports ADD FOREIGN KEY (reported_user_id) REFERENCES core.users (user_id);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX cities_country_id_idx ON core.cities (country_id);

CREATE INDEX universities_city_id_idx ON core.universities (city_id);

CREATE INDEX universities_orig_name_trgm_idx ON core.universities 
USING GIN (lower(original_name) gin_trgm_ops);

CREATE INDEX universities_eng_name_trgm_idx ON core.universities
USING GIN (lower(english_name) gin_trgm_ops);

CREATE INDEX users_nick_idx ON core.users (nick);

CREATE INDEX notif_sett_mail_idx ON core.notification_settings (mail);

CREATE INDEX princip_username_idx ON core.principals (username);

CREATE INDEX uni_reviews_author_id_idx ON core.university_reviews (author_id);

CREATE INDEX uni_reviews_uni_id_idx ON core.university_reviews (university_id);

CREATE INDEX review_reactions_review_id_idx ON core.university_reviews_reaction_counts (university_review_id);

CREATE INDEX exchanges_started_at_idx ON core.exchanges (started_at);

CREATE INDEX exchanges_end_at_idx ON core.exchanges (end_at);

CREATE INDEX exchanges_fos_id_idx ON core.exchanges (field_of_study_id);

CREATE INDEX exchanges_uni_id_idx ON core.exchanges (university_id);

CREATE INDEX reports_reporter_id_idx ON core.reports (reporter_id);

CREATE INDEX reports_status_created_at_idx ON core.reports (status, created_at DESC);

CREATE INDEX user_reports_reported_user_id_idx ON core.user_reports (reported_user_id);

CREATE INDEX univ_review_reports_review_id_idx ON core.university_review_reports (reported_review_id);

CREATE INDEX chat_reports_reported_user_id_idx ON core.chat_reports (reported_user_id);

CREATE INDEX chat_reports_room_id_idx ON core.chat_reports (room_id);
