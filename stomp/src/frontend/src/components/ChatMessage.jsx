import React from "react";

const ChatMessage = ({ message, writer, senderNickname, isRead }) => {
  return (
    <div
      className={`d-flex ${
        senderNickname === writer ? "justify-content-end" : ""
      } mt-2`}
    >
      <div
        className={`d-flex rounded-2 ${
          senderNickname === writer ? "sender-message" : "receiver-message"
        }`}
      >
        <div className="border-end border-dark pe-1">{writer}</div>
        <div className="ps-1">{message}</div>
      </div>
      <div>{isRead === "N" && 1}</div>
    </div>
  );
};

export default ChatMessage;
