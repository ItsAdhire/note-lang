(ns note-lang.wave.core
  (:require [note-lang.wave.ads :as ads]))

(defn- sin [x] 
 (Math/sin x))

(defn wave [freq vol bitrate duration]
  (let [domain  (* freq 2 Math/PI)
        amp     (partial * vol)
        stepper (partial * domain (/ bitrate))
        samples (* bitrate duration)]
    (->> (range)
         (map (comp amp sin stepper))
         (take samples))))

(def note->freq
  {:e4 329.63
   :a4 440
   :c5 523.25
   :e5 659.25
   :g5 783.99
   :b5 987.77
   :a5 880
   :rest 0})

(def post-processes
  {:ads-linear #(ads/ads-linear % 5)})

; TODO consider using (tree-seq)
(defn- song->nested-wave
  ([song vol bitrate bpm subd]
   (let [effbpm (* bpm subd)
         dur    (/ 60 effbpm)]
     (if (sequential? song)
       (map #(song->nested-wave % 
                                vol 
                                bitrate 
                                effbpm 
                                (inc subd)) 
            song)
       (wave (note->freq song) vol bitrate dur)))))

(defn song->wave [song vol bitrate bpm post-procs]
  (reduce #((%2 post-processes identity) %1) 
          (map #(song->nested-wave % vol bitrate bpm 1) song)
          post-procs))
