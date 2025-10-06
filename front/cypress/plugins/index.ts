/**
 * @type {Cypress.PluginConfig}
 */

export default (on: Cypress.PluginEvents, config: Cypress.PluginConfig) => {
  // `on` is used to hook into various events Cypress emits
  // `config` is the resolved Cypress config

  require('@cypress/code-coverage/task')(on, config);

  // It's IMPORTANT to return the config object
  // with any changed environment variables
  return config;
};
