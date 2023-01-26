import { createSlice } from "@reduxjs/toolkit";
import { useRef } from "react";

const stompSlice = createSlice({
  name: "stomp",
  initialState: { client: "" },
  reducers: {
    saveStomp: (state, action) => {
      state.client = action.payload;
      console.log(action.payload);
    },
  },
});

export const { saveStomp } = stompSlice.actions;
export default stompSlice;
