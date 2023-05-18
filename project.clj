(defproject jry "5.1.10-SNAPSHOT"
  :description "general clojure functions"
  :url "https://github.com/jaycfields/jry/blob/master/README.md"
  :license "https://github.com/jaycfields/jry/blob/master/README.md#license"
  :jar-name "jry.jar"
  :source-paths ["src/clojure"]
  :test-paths ["test"]
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :deploy-repositories [["releases" :clojars]]
  :profiles {:dev {:dependencies [[expectations "1.4.36"]]}}
  :plugins [[lein-expectations "0.0.7"]
            [lein-publishers "1.0.11"]]
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["deploy"]
                  ["publish-fig"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]])
