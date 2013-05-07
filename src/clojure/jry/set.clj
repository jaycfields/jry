(ns jry.set)

(defn rel->hash-map [rel key-fn & {:keys [val-fn] :or {val-fn identity}}]
  (reduce (fn [result e] (assoc result (key-fn e) (val-fn e))) {} rel))

(defn hash-map->rel [m kk vk]
  (map (fn [[k v]] (hash-map kk k vk v)) m))

(defn transform [rel transform-fns]
  (map #(reduce (fn [result [t-k t-fn]]
                  (update-in result [t-k] t-fn))
                %
                transform-fns)
       rel))

(defn- ->combined-values [combine-fns xmap ymap]
  (reduce (fn [result [c-k c-fn]]
            (if (and (contains? xmap c-k) (contains? ymap c-k))
              (assoc result c-k (c-fn (c-k xmap) (c-k ymap)))
              result))
          {}
          combine-fns))

(defn combine [rel combine-fns]
  (reduce (fn [xmap ymap]
            (merge xmap ymap (->combined-values combine-fns xmap ymap)))
          {}
          rel))
