import React from 'react';
import { Film } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { GenresView } from './GenresView';
import { ReviewView } from './ReviewView';

interface Props {
  film: Film,
  protocol: Protocol
}

export const FilmView: React.FC<Props> = ({film, protocol}) => {
  return (
    <>
      <div>Selected film</div>
      <div className={'table'}>
        <div className={'row'}>
          <div className={'col width_40'}>{film.id}</div>
          <div className={'col width_200'}>{film.title}</div>
          <div className={'col width_40'}>{film.year}</div>
        </div>
      </div>
      <GenresView genres={film.genres}/>
      {film.id && <ReviewView filmId={film.id} reviews={film.reviews} protocol={protocol}/>}
    </>
  );
};