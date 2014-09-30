(ns clojure.jry.io
  (require clojure.java.io))

(defn list-files [path & {:keys [path-filter] :or {path-filter #""}}]
  (->> (clojure.java.io/file path)
       .listFiles
       (filter (comp (partial re-find path-filter) clojure.java.io/as-relative-path))))

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
