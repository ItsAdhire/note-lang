(ns note-lang.wave.song
  (:require [note-lang.notes :as notes]
            [note-lang.wave.adsr :as adsr]
            [note-lang.wave.core :as wave]))


; TODO consider using (tree-seq)
(defn- song->nested-wave
  ([song vol bitrate bpm]
   (if (sequential? song)
     (map #(song->nested-wave % vol bitrate (* bpm (count song))) 
          song)
     (wave/wave (notes/note->freq song) vol bitrate (/ 60 bpm)))))

(defn song->wave [song vol bitrate bpm]
  (apply map + 
         (map flatten 
              (adsr/adsr-linear 
                song
                (map #(song->nested-wave % vol bitrate bpm) song)
                5 440 440 880))))
