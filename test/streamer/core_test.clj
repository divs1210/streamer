(ns streamer.core-test
  (:require [clojure.test :refer :all]
            [streamer.core :refer :all :as s]
            [net.cgrand.xforms :as x]))

(deftest tests
  (is (= (let [xf (comp (filter even?)
                        (map inc))]
           (sequence xf (range 10)))
         (=> (range 10)
             (filter even?)
             (map inc)))
      "only transducers")

  (is (= (first (reverse (sequence (map inc)
                                   (range 5))))
         (=> (range 5)
             (map inc)
             :seq
             reverse
             first))
      "transducers then seqs")

  (is (= (sequence (x/reduce str)
                   (reverse (sequence (map inc)
                                      (range 5))))
         (=> (range 5)
             (map inc)
             :seq
             reverse
             :stream
             (x/reduce str)))
      "transducers then seqs then transducers"))
