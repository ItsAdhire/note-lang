(ns note-lang.notes
  (:require [clojure.string :as str]))

;; TODO base tones off mid octave notes instead of 0 octave notes
;; to have more accurate frequenices for farther away octaves
(def base-tones 
  {:c  16.35
   :c# 17.32
   :d  18.35
   :d# 19.45
   :e  20.60
   :f  21.83
   :f# 23.12
   :g  24.5
   :g# 25.94
   :a  27.5
   :a# 29.14
   :b  30.87})

(defn- digit? [chr] (Character/isDigit chr))
(defn- parse-int [n] 
  (when (and (every? digit? n)
             (not-empty n))
    (Integer/parseInt n)))

(defn- parse-note [note]
  (let [[note_str octave_str] (->> (name note)
                                   (split-with (complement digit?))
                                   (mapv str/join))]
    [(keyword note_str) 
     (parse-int octave_str)]))

;; TODO use java exponents
(defn frequency [base-freq octave]
  (* (reduce * (repeat octave 2))
     base-freq))

(defn note->freq [note]
  (let [[tone octave] (parse-note note)
        base-freq (tone base-tones)]
    (if (and base-freq octave)
      (frequency base-freq octave)
      0)))
