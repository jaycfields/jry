(ns jry
  (:require clojure.data))

(defn xor
  [x y]
  (if (and x (not y))
    x
    (when (and y (not x))
      y)))

(defn every [pred l]
  (when (every? pred l)
    l))

(defn submap? [sub super]
  (->
    (clojure.data/diff sub super)
    last
    (= sub)))

(defn p [& xs]
  (apply println (map pr-str xs)))

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

(defn replace-vals [m replacements]
  (reduce
   (fn [r [k v]]
     (if (contains? r k)
       (assoc r k v)
       r))
   m replacements))

(defn one? [x] (= 1 x))
(defn two? [x] (= 2 x))

(defn k=
  ([k v] (fn [m] (and (= v (get m k)) m)))
  ([k1 v1 k2 v2] (fn [m] (and (= v1 (get m k1)) (= v2 (get m k2)) m)))
  ([k1 v1 k2 v2 & {:as kvs}] (fn [m]
                               (and
                                (= v1 (get m k1))
                                (= v2 (get m k2))
                                (= kvs (select-keys m (keys kvs)))
                                m))))

(defmacro kvify [& xs]
  (if (apply distinct? xs)
    (zipmap (map keyword xs) xs)
    (throw (RuntimeException. (str "duplicate keys to kvify: " xs)))))
