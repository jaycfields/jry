(ns expectations.jry-expectations
  (:use jry expectations))

(expect one? 1)
(expect two? 2)

(expect false (xor false false))
(expect true (xor false true))
(expect true (xor true false))
(expect false (xor true true))

(expect [1 2 3] (every identity [1 2 3]))
(expect nil (every identity [1 2 nil]))

(expect true? (submap? {:a 1 :b {:c 2}} {:a 1 :b {:c 2 :d 3} :e 4}))
(expect false? (submap? {:a 1 :b {:c 2}} {:a "9" :b {:c 2 :d 3} :e 4}))
(expect false? (submap? {:a 1 :b {:c 2}} {:a 1 :b {:c "9" :d 3} :e 4}))

(expect {:month 0 :year 2012 :day 31}
  (obj->map (java.util.Date. 2012 0 31)
    :month .getMonth
    :year .getYear
    :day .getDate))

(expect "\"i said\" {:foo \"bar\"}\n"
        (with-out-str (p "i said" {:foo "bar"})))

(expect {[:a :b] 1 [:a :e] 3 [:c :d] 2} (flatten-keys {:a {:b 1 :e 3} :c {:d 2}}))
(expect {[:a :z] nil [:a :b] 1 [:a :e :f] 4 [:a :e :g] 5}
  (flatten-keys {:a {:z nil :b 1 :e {:f 4 :g 5}}}))
(expect {} (flatten-keys {}))
(expect {} (flatten-keys nil))

(expect {:b {:e :f} :h {:e :f}}
  (update-vals {:b {:c :d :e :f} :h {:c :d :e :f}} dissoc :c))

(expect {1 :a 2 :b}
        (update-keys {2 :a 3 :b} - 1))

(expect [3 6]
  (nth-vals 2 {1 {2 3} 4 {5 6}}))

(expect [3 {6 7} :a]
  (nth-vals 2 {:a :a 1 {2 3} 4 {5 {6 7}}}))

(expect {:a 1 :b 3} (replace-vals {:a 1 :b 2} {:b 3 :c 4}))

(expect 10 (parse-long "10"))

(expect 2.1 (parse-double "2.1"))

(expect {:x "x", :one 1} ((k= :one 1) {:one 1 :x "x"}))
(expect {"a" "aye!"} ((k= "a" "aye!") {"a" "aye!"}))
(expect false ((k= :one 1) {:one 2 :x "x"}))

(expect {:x "x", :one 1, :two 2} ((k= :one 1 :two 2) {:one 1 :two 2 :x "x"}))
(expect false ((k= :one 1 :two 2) {:one 2 :two 2 :x "x"}))
(expect false ((k= :one 1 :two 2) {:one 1 :two 1 :x "x"}))

(expect {:x "x", :one 1, :two 2, :thr 3} ((k= :one 1 :two 2 :thr 3) {:one 1 :two 2 :thr 3 :x "x"}))
(expect false ((k= :one 1 :two 2 :thr 3) {:one 2 :two 2 :thr 3 :x "x"}))
(expect false ((k= :one 1 :two 2 :thr 3) {:one 1 :two 1 :thr 3 :x "x"}))
(expect false ((k= :one 1 :two 2 :thr 3) {:one 1 :two 2 :thr 1 :x "x"}))

(expect '{:a a :b b}
  (expanding (kvify a b)))
(expect RuntimeException (macroexpand-1 '(jry/kvify a a a a)))
