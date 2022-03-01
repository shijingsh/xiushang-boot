export default {
  get() {
    return window.sessionStorage.getItem('xiushang-TOKEN');
  },
  save(token) {
    window.sessionStorage.setItem('xiushang-TOKEN', token);
  },
};
