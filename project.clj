(defproject note_lang "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  ;:plugins [[cider/cider-nrepl "0.43.0"]] 
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cider/cider-nrepl "0.43.0"]]
  :repl-options {:init-ns note-lang.core})
