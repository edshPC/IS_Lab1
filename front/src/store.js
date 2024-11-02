import { configureStore } from '@reduxjs/toolkit';

const initialState = {
    token: null,
    logged_as: null,
};

const reducer = (state = initialState, {type, payload}) => {
    switch (type) {
        case 'SET':
            return { ...state, ...payload };
        case 'CLEAR_AUTH':
            return { ...state, token: null, logged_as: null };
        case 'ERROR':
            return { ...state, error: payload };
        case 'CLEAR_ERROR':
            return { ...state, error: null };
        default:
            return state;
    }
};

const store = configureStore({
    reducer,
    preloadedState: JSON.parse(localStorage.getItem('state')) || undefined
});

store.subscribe(() => localStorage.setItem('state', JSON.stringify(store.getState())));

export default store;
