(use-modules (ice-9 format))

(define test
  '(if (< 2 3) (println "2 < 3") (println "2 > 3")))

(define funcmap
  '((< 
(define (compile prog)
  (let* ((symbol (if (list? prog) (car prog) prog)))
	(cond ((eq? symbol 'if) (compile-if prog))
		  ((symbol? symbol) (compile-expr prog))
		  ((number? symbol) (gen-number prog))
		  (else (error 'compile (format #t "unknown symbol ~a" symbol))))))

(define (compile-expr expr)
  (let* ((func (car expr))
		 (left (cadr expr))
		 (right (caddr expr)))
	(begin
	  (compile left)
	  (compile right)
	  (display (format "~a " func)))))

(define (gen-number num)
  (display (format #f "ldc ~a " num))
  (newline))

(compile '(+ (- 5 2) (* 3 4)))
(newline)
