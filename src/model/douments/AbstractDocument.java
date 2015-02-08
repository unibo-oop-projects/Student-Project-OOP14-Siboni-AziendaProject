package model.douments;

import model.contatti.Contatto;

/**
 * Implementazione astratta di un Documeto
 * @author Enrico
 *
 */
public abstract class AbstractDocument implements Document {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7283770252215004660L;

	private final Contatto mittente;
	private final Contatto benefiaciario;
	private final Contatto debitore;
	private final String data;

	protected AbstractDocument(final Contatto mittente,
			final Contatto beneficiario, final Contatto debitore,
			final String data) {

		this.mittente = mittente;
		this.debitore = debitore;
		this.benefiaciario = beneficiario;
		this.data = data;
	}

	@Override
	public Contatto getMittente() {
		return this.mittente;
	}

	@Override
	public Contatto getBeneficiario() {
		return this.benefiaciario;
	}

	@Override
	public Contatto getDebitore() {
		return this.debitore;
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public abstract double getTotale();

}