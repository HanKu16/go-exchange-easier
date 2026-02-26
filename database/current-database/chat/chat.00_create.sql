CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE chat.rooms (
  room_id UUID PRIMARY KEY,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_message_id UUID
);

CREATE TABLE chat.user_in_rooms (
  room_id UUID,
  user_id INTEGER,
  last_read_at TIMESTAMPTZ,
  joined_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (room_id, user_id)
);

CREATE TABLE chat.messages (
  message_id UUID PRIMARY KEY,
  text_content TEXT NOT NULL,
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