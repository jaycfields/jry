(ns expectations.jry.set-expectations
  (:use expectations jry.set))

(expect {1 {:a 1 :b 1} 2 {:a 2 :b 2} 3 {:a 3 :b 3}}
        (rel->hash-map [{:a 1 :b 1} {:a 2 :b 2} {:a 3 :b 3}] :a))

(expect [{:x 1 :y 2} {:x 3 :y 4}]
        (hash-map->rel {1 2 3 4} :x :y))

(expect {3 {:a 3, :b 4}, 1 {:a 1, :b 2}}
        (rel->hash-map [{:a 1 :b 2} {:a 3 :b 4}] :a))

(expect {3 4 1 2}
        (rel->hash-map [{:a 1 :b 2} {:a 3 :b 4}] :a :val-fn :b))

(expect [{:a "1" :b 3 :c :d} {:a "3" :b 5 :c :e}]
        (transform [{:a 1 :b 2 :c :d} {:a 3 :b 4 :c :e}] {:a str :b inc}))

(expect {:a "13" :b 6 :c :d :e :f :should :take-mine}
        (combine [{:a "1" :b 2 :c :d :should :not-take-mine}
                  {:a "3" :b 4 :e :f :should :take-mine}] {:a str :b +}))
