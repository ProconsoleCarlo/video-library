export interface Film {
  id: number | null,
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
  id: number | null,
  date: string,
  rating: number,
  detail: string | null
}