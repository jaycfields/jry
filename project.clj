(defproject jry "5.1.0"
  :description "general clojure functions"
  :jar-name "jry.jar"
  :source-paths ["src/clojure"]
  :test-paths ["test"]
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :deploy-repositories [["releases" :clojars]]
  :profiles {:dev {:dependencies [[expectations "1.4.36"]]}}
  :plugins [[lein-expectations "0.0.7"]
            [lein-publishers "1.0.11"]])
