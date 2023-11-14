(ns carteira-app
  (:require [api :as api]
            [ui :as ui]))

  ;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;;; RODANDO A APLICAÇÃO ;;;
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn menu-principal []
  (loop []
    (ui/banner)
    (println "----------------------------------------------------")
    (ui/menu)
    (println "")
    (println "Escreva o número para selecionar a opção desejada:")
    (println "1) Ver Lista Completa de Companhias cadastradas na B3")
    (println "2) Exibir dados da ação de uma companhia")
    (println "3) Registrar transação de compra")
    (println "4) Registrar transação de venda")
    (println "5) Exibir extrato completo")
    (println "6) Exibir extrato por tipo (compra/venda)")
    (println "7) Exibir saldo total")
    (println "8) Sair")
    (println "----------------------------------------------------")
    (println "Sua Escolha: ")

    (let [selecao (Integer. (read-line))]
      (cond
        (= selecao 1) (do
                        (api/exibir-lista-companhias)
                        (recur))
        (= selecao 2) (do
                        (println "Digite o nome da companhia:")
                        (api/exibir-dados-acao (read-line))
                        (recur))
        (= selecao 3) (do
                        (println "Opção não implementada ainda. Tente novamente.")
                        (recur))
        (= selecao 4) (do
                        (println "Opção não implementada ainda. Tente novamente.")
                        (recur))
        (= selecao 5) (do
                        (println "Opção não implementada ainda. Tente novamente.")
                        (recur))
        (= selecao 6) (do
                        (println "Opção não implementada ainda. Tente novamente.")
                        (recur))
        (= selecao 7) (do
                        (println "Opção não implementada ainda. Tente novamente.")
                        (recur))
        (= selecao 8) (do
                        (println "Saindo...")
                        (ui/fim))
        :else (do
                (println "Opção inválida. Tente novamente.")
                (recur))))))

(menu-principal)



