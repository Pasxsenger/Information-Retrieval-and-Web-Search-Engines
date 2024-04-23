import { Config } from "./src/config";

export const defaultConfig: Config = {
  url: "https://aws.amazon.com/certification/certified-cloud-practitioner/?nc1=h_ls",
  match: "https://aws.amazon.com/certification/**",
  maxPagesToCrawl: 50,
  outputFileName: "output.json",
  maxTokens: 2000000,
};
