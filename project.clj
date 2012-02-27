(defproject jry "1.0.1"
  :description "general clojure functions"
  :jar-name "jry.jar"
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :dev-dependencies [[expectations "1.3.5"]
                     [lein-expectations "0.0.1"]])

(ns leiningen.publish-fig
  (:require leiningen.jar)
  (:use clojure.java.shell))

(defn publish-fig [project & args]
  (leiningen.jar/jar project)
  (let [response (apply sh "fig" "--publish" (str (:name project) "/" (:version project)) args)]
    (println "OUT:" (:out response))
    (println "ERR:" (:err response))))

(ns leiningen.publish-clojars
  (:require leiningen.jar)
  (:use clojure.java.shell))

(defn publish-clojars [project & args]
  (leiningen.jar/jar project)
  (leiningen.pom/pom project)
  (let [response (sh "scp" "pom.xml" "jry.jar" "clojars@clojars.org:")]
    (println "OUT:" (:out response))
    (println "ERR:" (:err response))))
