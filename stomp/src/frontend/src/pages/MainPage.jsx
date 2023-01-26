import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { Button } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  return (
    <div className="layout position-relative">
      <Button
        onClick={() => {
          navigate("/public-chat-room");
        }}
      >
        공개 채팅방
      </Button>
    </div>
  );
};

export default MainPage;
