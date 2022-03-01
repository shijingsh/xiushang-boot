export default {
  get() {
    return localStorage.getItem('xiushang-TOKEN');
  },
  save(token) {
    localStorage.setItem('xiushang-TOKEN', token);
  },
  delete() {
    localStorage.removeItem('xiushang-TOKEN');
  },
};
