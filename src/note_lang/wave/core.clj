(ns note-lang.wave.core
  (:require [note-lang.wave.ads :as ads]
            [note-lang.notes :as notes]))

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

(def post-processes
  {:ads-linear #(ads/ads-linear % 2)})

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
       (wave (notes/note->freq song) vol bitrate dur)))))

(defn song->wave [song vol bitrate bpm post-procs]
  (reduce #((%2 post-processes identity) %1) 
          (map #(song->nested-wave % vol bitrate bpm 1) song)
          post-procs))
