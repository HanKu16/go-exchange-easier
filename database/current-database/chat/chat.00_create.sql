CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE chat.rooms (
  room_id UUID PRIMARY KEY,
  last_message_at TIMESTAMPTZ,
  last_message_text_content TEXT,
  last_message_author_id INTEGER,
  last_message_author_nick VARCHAR(20),
  last_message_author_avatar_key VARCHAR(255)
);

CREATE TABLE chat.user_in_rooms (
  room_id UUID,
  user_id INTEGER,
  last_read_at TIMESTAMPTZ,
  PRIMARY KEY (room_id, user_id)
);

CREATE TABLE chat.messages (
  message_id UUID PRIMARY KEY,
  text_content VARCHAR(1000) NOT NULL,
  avatar_key VARCHAR(255),
  nick VARCHAR(20) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  deleted_at TIMESTAMPTZ,
  room_id UUID NOT NULL,
  author_id INTEGER NOT NULL
);

ALTER TABLE chat.user_in_rooms ADD FOREIGN KEY (room_id) REFERENCES chat.rooms (room_id);

ALTER TABLE chat.messages ADD FOREIGN KEY (room_id) REFERENCES chat.rooms (room_id);

CREATE INDEX idx_chat_messages_room_history ON chat.messages (room_id, created_at DESC) 
WHERE deleted_at IS NULL;

CREATE INDEX idx_chat_user_in_rooms_user_id ON chat.user_in_rooms (user_id);