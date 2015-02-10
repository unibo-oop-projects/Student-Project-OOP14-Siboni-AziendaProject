package model.operation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.conto.Conto;
import model.conto.Conto.Eccedenza;
import model.douments.Data;
import model.douments.DataImpl;

public class OperationImpl implements Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445965986132678879L;

	private final Map<Conto, Double> map;
	private final Data data;
	private boolean movementsApplied;
	private Optional<String> description;

	public OperationImpl() {
		this.map = new HashMap<>();
		this.data = new DataImpl();
		this.description = Optional.empty();
	}

	@Override
	public void setContoMovimentato(final Conto c, final double importo) {
		if (!this.movementsApplied) {
			if (importo == 0) {
				throw new IllegalArgumentException(
						"L'importo inserito è uguale a zero!");
			}
			this.map.put(c, importo);
		}

	}

	@Override
	public void setDescription(final String description) {
		this.description = Optional.ofNullable(description);
	}


	@Override
	public Data getData() {
		return this.data;
	}

	@Override
	public boolean isBalanced() {
		final double result = this.map
				.entrySet()
				.stream()
				.filter(e -> e.getKey().getEccedenzaSolita()
						.equals(Eccedenza.DARE)).mapToDouble(e -> e.getValue())
				.sum()
				- this.map
						.entrySet()
						.stream()
						.filter(e -> e.getKey().getEccedenzaSolita()
								.equals(Eccedenza.AVERE))
						.mapToDouble(e -> e.getValue()).sum();

		return result > -0.001 && result < 0.001;
	}

	@Override
	public boolean haveMovementsBeenApplied() {
		return this.movementsApplied;
	}

	@Override
	public void applicaMovimenti() {
		if (!this.movementsApplied) {
			if (this.isBalanced()) {
				this.map.entrySet().stream()
						.forEach(e -> e.getKey().addMovimento(e.getValue()));
				this.movementsApplied = true;
			} else {
				throw new IllegalStateException(
						"L'operazione non è bilanciata!");
			}
		}

	}

	@Override
	public Set<Conto> getContiMovimentati() {
		return this.map.keySet();
	}

	@Override
	public Map<Conto, Double> getContiMovimentatiEImporto() {
		return this.map.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
	}

	@Override
	public String getDescription() {
		return this.description.orElse("");
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationImpl other = (OperationImpl) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String result = "Data=" + data
				+ ",\nMovimenti=" + map;
		if (description.isPresent()) {
			result += ",\nDescrizione=" + description.get();
		}
		return result;
	}

}
