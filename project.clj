(defproject jry "3.0.0"
  :description "general clojure functions"
  :jar-name "jry.jar"
  :source-paths ["src/clojure"]
  :test-paths ["test"]
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :profiles {:dev {:dependencies [[expectations "1.4.36"]]}}
  :plugins [[lein-expectations "0.0.7"]
            [lein-publishers "1.0.10"]])
