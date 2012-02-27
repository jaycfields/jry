(ns jry.core
  (:require clojure.data))

(defn % [& args] (apply partial args))

(defn truthy? [x]
  (if x true false))

(def falsey? (complement truthy?))

(defn every [pred l]
  (when (every? pred l)
    l))

(defn submap [sub super]
  (->
    (clojure.data/diff sub super)
    last
    (= sub)))

(defmacro returning [[the-symbol v] & forms]
  `(let [~the-symbol ~v]
     ~@forms
     ~the-symbol))
