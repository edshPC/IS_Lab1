import { configureStore } from '@reduxjs/toolkit';

const to_store = ["token", "logged_as"];

const initialState = {
    token: null,
    logged_as: null,
    data: [],
    current_dragon: null,
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

export function updateState(payload) {
    return {type: 'SET', payload: payload}
}

const store = configureStore({
    reducer,
    preloadedState: {...initialState, ...(JSON.parse(localStorage.getItem('state')) || {})},
});

store.subscribe(() => {
    const state = store.getState();
    const filteredState = {};
    to_store.forEach(key => {
        if (state[key] !== undefined) {
            filteredState[key] = state[key];
        }
    });
    localStorage.setItem('state', JSON.stringify(filteredState));
});

export default store;
