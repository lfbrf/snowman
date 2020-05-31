package br.com.snowman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.snowman.model.TouristSpotPicture;
/**
 * @author luiz
 *  Repositório das imagens dos pontos turísticos
 *  Insere manualmente as fotos na tabela de imagens (sem uso do crudRepoistory .save) pois ao tentar inserir um novo 
 *  registro contendo o mesmo usuário e para memso ponto turístico o .save não atende a necessidade
 */
@Repository
public interface TouristSpotPictureRepository extends JpaRepository<TouristSpotPicture, Long> {
	
	@Modifying
	@Query(value = "insert into tourist_picture (picture, name_picture, tourist_id, user_id) VALUES (:picture, :name_picture, :tourist_id, :user_id)", nativeQuery = true)
	@Transactional
	public int insertInPictureTouristSpot(@Param("picture") byte[] bs, @Param("name_picture") String namePicture, @Param("tourist_id") long touristId, @Param("user_id") long userId);
	
}	