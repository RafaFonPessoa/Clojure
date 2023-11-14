;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Este código serve apenas para printas as UI no terminal ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(ns ui)

;; Banner do menu principal da aplicação

(defn banner []
  (println "   ____           _       _                 _           _        /\\/|")
  (println "  / ___|__ _ _ __| |_ ___(_)_ __ __ _    __| | ___     / \\   ___|/\\/   ___  ___")
  (println " | |   / _` | '__| __/ _ \\ | '__/ _` |  / _` |/ _ \\   / _ \\ / __/ _ \\ / _ \\/ __|")
  (println " | |__| (_| | |  | ||  __/ | | | (_| | | (_| |  __/  / ___ \\ (_| (_) |  __/\\__ \\")
  (println "  \\____\\__,_|_|   \\__\\___|_|_|  \\__,_|  \\__,_|\\___| /_/   \\_\\___\\___/ \\___||___/")
  (println "                                                             )_)"))

(defn menu []
  (println "__  __")
  (println "|  \\/  |___ __ _ _  _ ")
  (println "| |\\/| / -_) ' \\ || |")
  (println "|_|  |_\\___|_||_\\_,_|"))

;;Agradecimento de por usar a aplicação

(defn fim []
  (println " ___ _")
  (println " | __(_)_ __")
  (println " | _|| | '  \\ ")
  (println " |_| |_|_|_|_| "))