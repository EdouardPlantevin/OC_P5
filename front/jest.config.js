module.exports = {
  moduleNameMapper: {
    '@core/(.*)': '<rootDir>/src/app/core/$1',
  },
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  bail: false,
  verbose: false,
  collectCoverage: true,
  coverageDirectory: './coverage/jest',
  coverageReporters: ['html', 'text-summary'],
  collectCoverageFrom: [
    '<rootDir>/src/**/*.ts',
    '!<rootDir>/src/**/*.spec.ts',
    '!<rootDir>/src/main.ts',
    '!<rootDir>/src/polyfills.ts',
    '!<rootDir>/src/environments/**',
    '!<rootDir>/src/app/**/interfaces/**',
    '!<rootDir>/src/**/*.module.ts',
    '!<rootDir>/src/**/*-routing.module.ts',
    '!**/index.ts'
  ],
  testPathIgnorePatterns: [
    '<rootDir>/node_modules/'
  ],
  coveragePathIgnorePatterns: ['<rootDir>/node_modules/'],
  coverageThreshold: {
    global: {
      statements: 80,
      branches: 80,
      functions: 80,
      lines: 80
    },
  },
  roots: [
    "<rootDir>"
  ],
  modulePaths: [
    "<rootDir>"
  ],
  moduleDirectories: [
    "node_modules"
  ],
};
