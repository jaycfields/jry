# jry

> another conjrib

----------

## Installing


The easiest way to use expectations in your own projects is via
[Leiningen](http://github.com/technomancy/leiningen). Add the
following dependency to your project.clj file:

    [jry "1.0.10"]

To build expectations from source, run the following commands:

    lein deps
    lein jar

## Examples

composing functions
    
    ; % as a shorthand for partial
    ((% identity 1)) => 1
    
basic functions

    ; exclusive or
    (xor true false) => true
    (xor true true) => false
    (xor false false) => false
    
    ; one? & two?
    (one? 1) => true
    (one? 3) => false
    (two? 2) => true
    (two? 8) => false

interop related functions

    ; truthy converts truthy values to true and falsey values to false
    ; good for functions that need to return true or false explicitly
    (truthy? "BRN") => true
    (truthy? true)  => true
    (truthy? nil)   => false

    ; falsey is the complement of truthy
    (falsey? "BRN") => false
    (falsey? true)  => false
    (falsey? nil)   => true

    ; obj->map allows you to convert from a Java object to a map
    ; without using reflection
    (obj->map (java.util.Date. 2012 0 31)
              :month .getMonth
              :year .getYear
              :day .getDate) => {:month 0 :year 2012 :day 31}
              
    ; parse-double & parse-long are available, and helpful when used with high-order fns
    (parse-double "2.1") => 2.1
    (parse-long "2") => 2

collections
   
    ; every is like every?, but it returns the entire list if
    ; everything is truthy; else, nil
    (every identity [1 2 3]) => [1 2 3]
    (every identity [1 false]) => nil

maps

    ; submap returns true if a map is (recursively) a submap
    (submap {:a 1 :b {:c 2}} {:a 1 :b {:c 2 :d 3} :e 4}) => true
    (submap {:a 1 :b {:c 2}} {:a "9" :b {:c 2 :d 3} :e 4}) => false
    (submap {:a 1 :b {:c 2}} {:a 1 :b {:c "9" :d 3} :e 4}) => false

    ; flatten-keys denormalizes keys.
    (flatten-keys {:a {:b 1 :e 3} :c {:d 2}}) => {[:a :b] 1 [:a :e] 3 [:c :d] 2}
    (flatten-keys {:a {:z nil :b 1 :e {:f 4 :g 5}}}) => {[:a :z] nil [:a :b] 1 [:a :e :f] 4 [:a :e :g] 5} 

    ; update-values applies a function (and any additional args) to
    ; each value in a map. 
    (update-values {:b {:c :d :e :f} :h {:c :d :e :f}} dissoc :c) => {:b {:e :f} :h {:e :f}}

    ; update-keys applies a function (and any additional args) to
    ; each key in a map.
    (update-keys {1 :a 2 :b} + 1) => {2 :a 3 :b}

    ; nth-values returns all values at or below a key depth
    (nth-vals 2 {1 {2 3} 4 {5 6}}) => [3 6]
    (nth-vals 2 {:a :a 1 {2 3} 4 {5 {6 7}}}) => [3 {6 7} :a]

    ; key-by keys a new map from an xrel by applying a fn to each
    ; element of an xrel
    (key-by :a [{:a 1 :b 1} {:a 2 :b 2} {:a 3 :b 3}]) => {1 {:a 1 :b 1} 2 {:a 2 :b 2} 3 {:a 3 :b 3}}

    ; xrelify converts a map into an xrel, mapping each k/v pair to a
    ; k key and a v key
    (xrelify {1 2 3 4} :x :y) => [{:x 1 :y 2} {:x 3 :y 4}]

    ; replace-values replaces the values of keys that exist in a map
    ; (and ignores any replacement values that have keys that do not
    ; exist in the original map)
    (replace-values {:a 1 :b 2} {:b 3 :c 4}) => {:a 1 :b 3}
    
## License

Copyright (c) 2010, Jay Fields
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

* Neither the name Jay Fields nor the names of the contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
