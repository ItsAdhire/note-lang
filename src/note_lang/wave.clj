(ns note-lang.wave)

(defn- sin [x] 
 (Math/sin x))

(defn wave [freq vol bitrate duration]
  (let [domain  (* freq 2 Math/PI)
        amp     (partial * vol)
        step    (partial * domain (/ bitrate))
        samples (* bitrate duration)]
    (->> (range)
         (map (comp amp sin step))
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

; TODO consider using (tree-seq)
(defn song->wave 
  ([song vol bitrate bpm]
   (mapcat #(song->wave % vol bitrate bpm 1) song))
  ([song vol bitrate bpm subd]
   (let [effbpm (* bpm subd)
         dur    (/ 60 effbpm)]
     (if (sequential? song)
       (mapcat #(song->wave % vol bitrate effbpm (inc subd)) song)
       (wave (note->freq song) vol bitrate dur)))))
