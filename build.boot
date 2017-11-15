(def project
  {:project     'integrated
   :version     "0.1.0"
   :description "Testing cljs/js integration."})

;;;; Dependencies

(def dependencies
  "Main app dependencies."
  '[[org.clojure/clojure          "1.9.0-beta1"]
    [org.clojure/clojurescript    "1.9.946"]])

(def build-dependencies
  "Dependencies to build the system."
  '[[adzerk/boot-cljs             "2.1.4"    :scope "test"]
    [adzerk/boot-cljs-repl        "0.3.3"    :scope "test"]
    [adzerk/boot-reload           "0.5.2"    :scope "test"]
    [pandeiro/boot-http           "0.8.3"]
    [org.clojure/tools.nrepl      "0.2.13"   :scope "test"]
    [com.cemerick/piggieback      "0.2.2"    :scope "test"]
    [weasel                       "0.7.0"    :scope "test"]])

;;;; Environment

(set-env! :dependencies   #(vec (concat %
                                        dependencies
                                        build-dependencies))
          :source-paths   #{"src/clojure" "src/js"}
          :resource-paths #{"src/resources"})

;;;; Build Tasks

(require '[adzerk.boot-cljs :refer [cljs]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[adzerk.boot-reload :refer [reload]]
         '[pandeiro.boot-http :refer [serve]])

;;;; Tasks

(deftask dev
  "Run development system."
  [r nrepl-port VALUE int "nREPL port"]
  (comp (watch)
        (reload :on-jsload 'cljs.integrated.core/init!)
        (cljs-repl :nrepl-opts {:port nrepl-port})
        (cljs)
        (target :dir #{"resources"})
        (serve :port 5050)))

