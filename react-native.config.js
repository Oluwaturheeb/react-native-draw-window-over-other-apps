module.exports = {
  dependency: {
    platforms: {
      android: {
        packageInstance: 'new DrawOverOtherAppsViewPackage()',
        sourceDir: './android',
      },
    },
  },
  project: {
    android: {
      sourceDir: './example/android',
    },
  },
};
