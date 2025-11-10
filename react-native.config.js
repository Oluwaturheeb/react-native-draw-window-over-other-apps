module.exports = {
  dependency: {
    platforms: {
      android: {
        packageInstance: 'new DrawOverOtherAppsPackage()',
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
