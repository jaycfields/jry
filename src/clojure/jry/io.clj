(ns jry.io
  (require clojure.java.io))

(defn- list-files* [path path-filter]
  (->> (clojure.java.io/file path)
       .listFiles
       (filter (comp (partial re-find path-filter) clojure.java.io/as-relative-path))))

(defn list-files [path & {:keys [path-filter recursive] :or {path-filter #"" recursive false}}]
  (if recursive
    (loop [result []
           [f & rest-files] (list-files* "./lyndon-prod")]
      (cond
       (nil? f) result
       (.isDirectory f) (recur result (concat rest-files (list-files* f)))
       :else (recur (conj result f) rest-files)))
    (list-files* path path-filter)))

(defprotocol CompilableCljSrcFile
  (compilable-clj-src-file? [s]))

(extend-protocol CompilableCljSrcFile
  String
  (compilable-clj-src-file? [s]
    (try
      (-> s slurp load-string)
      true
      (catch clojure.lang.Compiler$CompilerException e false)))

  java.io.File
  (compilable-clj-src-file? [f]
    (compilable-clj-src-file? (.getPath f))))
