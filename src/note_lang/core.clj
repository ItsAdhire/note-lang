(ns note-lang.core
  (:require [note-lang.audio :as audio]))

(def song (take 120 (cycle '(:a4 :c5 :e5 :c5 :e5 :c5 :a4 :c5))))
(def song2 (take 120 (cycle '(:e5 :g5 :b5 :g5 :b5 :g5 :e5 :g5))))
(def song3 (take 240 (cycle (concat 
                              (repeat 8 :rest)
                              '(:e4 :e5 :e4 :e5)
                              (repeat 4 :rest)))))

(def time-scale 0.5)
(def bitrate 8800)
(def volume 4)

