(ns jry.test.core
  (:use jry.core expectations))

(expect fn? (% identity 1))
(expect 1 ((% identity 1)))

(expect 1 (returning [x 1] (* 2 x) (* 4 x)))

(expect true (truthy? "BRN"))
(expect true (truthy? true))
(expect true (truthy? 1))
(expect false (truthy? nil))
(expect false (truthy? false))

(expect false (falsey? "BRN"))
(expect false (falsey? true))
(expect false (falsey? 1))
(expect true (falsey? nil))
(expect true (falsey? false))

(expect [1 2 3] (every identity [1 2 3]))
(expect nil (every identity [1 2 nil]))

(expect true? (submap {:a 1 :b {:c 2}} {:a 1 :b {:c 2 :d 3} :e 4}))
(expect false? (submap {:a 1 :b {:c 2}} {:a "9" :b {:c 2 :d 3} :e 4}))
(expect false? (submap {:a 1 :b {:c 2}} {:a 1 :b {:c "9" :d 3} :e 4}))

(expect {:month 0 :year 2012 :day 31}
  (obj->map (java.util.Date. 2012 0 31)
    :month .getMonth
    :year .getYear
    :day .getDate))

(expect {[:a :b] 1 [:a :e] 3 [:c :d] 2} (flatten-keys {:a {:b 1 :e 3} :c {:d 2}}))
(expect {[:a :z] nil [:a :b] 1 [:a :e :f] 4 [:a :e :g] 5}
  (flatten-keys {:a {:z nil :b 1 :e {:f 4 :g 5}}}))
(expect {} (flatten-keys {}))
(expect {} (flatten-keys nil))

(expect {:b {:e :f} :h {:e :f}}
  (update-values {:b {:c :d :e :f} :h {:c :d :e :f}} dissoc :c))

(expect [3 6]
  (nth-vals 2 {1 {2 3} 4 {5 6}}))

(expect [3 {6 7} :a]
  (nth-vals 2 {:a :a 1 {2 3} 4 {5 {6 7}}}))

(expect {1 {:a 1 :b 1} 2 {:a 2 :b 2} 3 {:a 3 :b 3}}
  (key-by :a [{:a 1 :b 1} {:a 2 :b 2} {:a 3 :b 3}]))
