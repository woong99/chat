import { createSlice } from '@reduxjs/toolkit';
import { useRef } from 'react';

const stompSlice = createSlice({
  name: 'stomp',
  initialState: { client: '', subscriptions: [] },
  reducers: {
    saveStomp: (state, action) => {
      state.client = action.payload;
    },
    addSubscription: (state, action) => {
      state.subscriptions = [...state.subscriptions, action.payload];
    },
    removeSubscription: (state, action) => {
      state.subscriptions = state.subscriptions.filter(
        (item) => item.roomId !== action.payload.roomId,
      );
    },
  },
});

export const { saveStomp, addSubscription, removeSubscription } = stompSlice.actions;
export default stompSlice;
