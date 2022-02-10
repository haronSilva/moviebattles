package com.letscode.moviesbattle.to;

import java.util.List;

public class RespostaRankingTO extends RespostaTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7042881825720099605L;
	private List<RankingTO> listaRanking;
	
	public List<RankingTO> getListaRanking() {
		return listaRanking;
	}
	public void setListaRanking(List<RankingTO> listaRanking) {
		this.listaRanking = listaRanking;
	}
	
}
