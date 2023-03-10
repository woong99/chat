import React, { useEffect, useRef } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import useStomp from "../hooks/useStomp";
import { saveStomp } from "../redux/modules/stomp";

const ConnectStompPage = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const client = useRef({});
  const [connect, disconnect] = useStomp(client, "/sub/notice");
  useEffect(() => {
    connect();
    dispatch(saveStomp(client));
    navigate("/");
  }, []);
  return <div>test</div>;
};

export default ConnectStompPage;
