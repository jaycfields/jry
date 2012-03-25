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

(defmacro ^{:private true} assert-args [fnname & pairs]
  `(do (when-not ~(first pairs)
    (throw (IllegalArgumentException.
      ~(str fnname " requires " (second pairs)))))
    ~(let [more (nnext pairs)]
      (when more
        (list* `assert-args fnname more)))))

(defmacro obj->map [o & bindings]
  (assert-args obj->map
    (even? (count bindings)) "an even number of forms in binding vector")
  (let [s (gensym "local")]
    `(let [~s ~o]
      ~(->> (apply hash-map bindings)
        (map (fn [[k v]] [k (list v s)]))
        (into {})))))

(defn nth-vals* [a i m]
  (if (and (map? m) (> i 0))
    (reduce into a (map (fn [v] (nth-vals* a (dec i) v)) (vals m)))
    (conj a m)))

(defn nth-vals [i m]
  (if (nil? m)
    {}
    (nth-vals* [] i m)))

(defn flatten-keys* [a ks m]
  (if (map? m)
    (reduce into {} (map (fn [[k v]] (flatten-keys* a (conj ks k) v)) (seq m)))
    (assoc a ks m)))

(defn flatten-keys [m]
  (if (nil? m)
    {}
    (flatten-keys* {} [] m)))

(defn nth-vals* [a i m]
  (if (and (map? m) (> i 0))
    (reduce into a (map (fn [v] (nth-vals* a (dec i) v)) (vals m)))
    (conj a m)))

(defn nth-vals [i m]
  (if (nil? m)
    {}
    (nth-vals* [] i m)))

(defn update-values [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn update-keys [m f & args]
  (reduce (fn [r [k v]] (assoc r (apply f k args) v)) {} m))

(defn key-by [f coll]
  (reduce (fn [r e] (assoc r (f e) e)) {} coll))

(defn xrelify [m kk vk]
  (map (fn [[k v]] (hash-map kk k vk v)) m))

(defn replace-values [m replacements]
  (reduce
   (fn [r [k v]]
     (if (contains? r k)
       (assoc r k v)
       r))
   m replacements))

(def parse-double #(Double/parseDouble %))
(def parse-long #(Long/parseLong %))
