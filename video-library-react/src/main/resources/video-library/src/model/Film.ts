export interface Film {
  id: number,
  title: string,
  year: number,
  genres: Genre[],
  reviews: Review[]
}

export interface Genre {
  id: number,
  value: string
}

export interface Review {
  id: number,
  date: string,
  rating: number,
  detail: string | null
}