import { configureStore } from '@reduxjs/toolkit';
import authSlice from './modules/auth';
import stompSlice from './modules/stomp';
import logger from 'redux-logger';

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    stomp: stompSlice.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }).concat(logger),
  devTools: process.env.NODE_ENV !== 'production',
});

export default store;
