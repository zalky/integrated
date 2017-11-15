goog.provide('js.lib');

js.lib.debugMessage = (x = "any old string") => {
  console.log(`Printing ${x} from cljs!`);
};
