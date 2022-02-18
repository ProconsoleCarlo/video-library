import React from 'react';
import { Genre } from '../model/Film';

type Props = {
  genres: Genre[];
}

export const GenresView: React.FC<Props> = ({genres}) => {
  return (
    <>
      <div className={'row'}>Genres</div>
      <div className={'table'}>
        {
          genres.map((genre) =>
            <div className={'row'}>
              <div className={'col width_200'}>{genre.value}</div>
            </div>
          )
        }
      </div>
    </>
  );
};