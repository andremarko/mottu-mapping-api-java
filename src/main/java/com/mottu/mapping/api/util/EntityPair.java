package com.mottu.mapping.api.util;

import java.util.Optional;

public record EntityPair<A, B, C>(A first, B second, Optional<C> third) {}
